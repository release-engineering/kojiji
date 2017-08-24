/**
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.kerberos;

import java.time.temporal.ChronoField;

import org.apache.kerby.kerberos.kerb.KrbErrorCode;
import org.apache.kerby.kerberos.kerb.KrbException;
import org.apache.kerby.kerberos.kerb.common.EncryptionUtil;
import org.apache.kerby.kerberos.kerb.type.KerberosTime;
import org.apache.kerby.kerberos.kerb.type.ap.ApOption;
import org.apache.kerby.kerberos.kerb.type.ap.ApOptions;
import org.apache.kerby.kerberos.kerb.type.ap.ApReq;
import org.apache.kerby.kerberos.kerb.type.ap.Authenticator;
import org.apache.kerby.kerberos.kerb.type.base.EncryptedData;
import org.apache.kerby.kerberos.kerb.type.base.EncryptionKey;
import org.apache.kerby.kerberos.kerb.type.base.KeyUsage;
import org.apache.kerby.kerberos.kerb.type.base.PrincipalName;
import org.apache.kerby.kerberos.kerb.type.ticket.EncTicketPart;
import org.apache.kerby.kerberos.kerb.type.ticket.TgtTicket;
import org.apache.kerby.kerberos.kerb.type.ticket.Ticket;

/**
 * A wrapper for ApReq request
 * The client principal and sgt ticket are needed to create ApReq message.
 */
public class ApRequest {

    private PrincipalName clientPrincipal;
    private TgtTicket sgtTicket; // upstream uses SgtTicket, but it's broken, cf. DIRKRB-234, DIRKRB-472
    private ApReq apReq;

    public ApRequest(PrincipalName clientPrincipal, TgtTicket sgtTicket) {
        this.clientPrincipal = clientPrincipal;
        this.sgtTicket = sgtTicket;
    }

    public ApReq getApReq() throws KrbException {
        if (apReq == null) {
            apReq = makeApReq();
        }
        return apReq;
    }

    public void setApReq(ApReq apReq) {
        this.apReq = apReq;
    }

    private ApReq makeApReq() throws KrbException {
        ApReq apReq = new ApReq();

        Authenticator authenticator = makeAuthenticator();
        EncryptionKey sessionKey = sgtTicket.getSessionKey();
        EncryptedData authData = EncryptionUtil.seal(authenticator,
                sessionKey, KeyUsage.AP_REQ_AUTH);
        apReq.setEncryptedAuthenticator(authData);
        apReq.setAuthenticator(authenticator);
        apReq.setTicket(sgtTicket.getTicket());
        ApOptions apOptions = new ApOptions();
        apOptions.setFlag(ApOption.MUTUAL_REQUIRED); // upstream doesn't allow setting options, forces USE_SESSION_KEY, so just force what we want here
        apReq.setApOptions(apOptions);

        return apReq;
    }

    /*
     * Make the Authenticator for ApReq.
     */
    private Authenticator makeAuthenticator() throws KrbException {
        Authenticator authenticator = new Authenticator();
        authenticator.setAuthenticatorVno(5);
        authenticator.setCname(clientPrincipal);
        authenticator.setCrealm(sgtTicket.getRealm());
        KerberosTime now = KerberosTime.now();
        authenticator.setCtime(now);
        authenticator.setCusec(now.getValue().toInstant().get(ChronoField.MICRO_OF_SECOND)); // upstream doesn't set usec
        // upstream doesn't set seq number
        // upstream forces use of sub key which is set to the session key
        return authenticator;
    }

    /*
     *  Validate the ApReq.
     */
    public static void validate(EncryptionKey encKey, ApReq apReq) throws KrbException {
        Ticket ticket = apReq.getTicket();

        if (encKey == null) {
            throw new KrbException(KrbErrorCode.KRB_AP_ERR_NOKEY);
        }
        EncTicketPart encPart = EncryptionUtil.unseal(ticket.getEncryptedEncPart(),
                encKey, KeyUsage.KDC_REP_TICKET, EncTicketPart.class);
        ticket.setEncPart(encPart);

        unsealAuthenticator(encPart.getKey(), apReq);

        Authenticator authenticator = apReq.getAuthenticator();
        if (!authenticator.getCname().equals(ticket.getEncPart().getCname())) {
            throw new KrbException(KrbErrorCode.KRB_AP_ERR_BADMATCH);
        }
        if (!authenticator.getCrealm().equals(ticket.getEncPart().getCrealm())) {
            throw new KrbException(KrbErrorCode.KRB_AP_ERR_BADMATCH);
        }
    }

    /*
     *  Unseal the authenticator through the encryption key from ticket
     */
    public static void unsealAuthenticator(EncryptionKey encKey, ApReq apReq) throws KrbException {
        EncryptedData authData = apReq.getEncryptedAuthenticator();

        Authenticator authenticator = EncryptionUtil.unseal(authData,
                encKey, KeyUsage.AP_REQ_AUTH, Authenticator.class);
        apReq.setAuthenticator(authenticator);
    }
}

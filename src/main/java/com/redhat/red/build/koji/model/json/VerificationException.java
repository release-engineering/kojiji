package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.KojiClientException;
import org.commonjava.maven.atlas.ident.util.JoinString;

import java.util.Set;

/**
 * Created by jdcasey on 2/16/16.
 */
public class VerificationException
        extends KojiClientException
{
    private Set<String> missingProperties;

    public VerificationException( Set<String> missing )
    {
        super( "The following properties were missing:\n  -%s", new JoinString( "\n  -", missing ) );
        this.missingProperties = missing;
    }

    public Set<String> getMissingProperties()
    {
        return missingProperties;
    }
}

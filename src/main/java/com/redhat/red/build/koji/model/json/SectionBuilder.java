package com.redhat.red.build.koji.model.json;

import java.util.Set;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/16/16.
 */
public interface SectionBuilder<T>
{
    T build()
            throws VerificationException;

}

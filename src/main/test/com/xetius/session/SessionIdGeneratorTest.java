package com.xetius.session;

import org.junit.Assert;
import org.junit.Test;

public class SessionIdGeneratorTest {
    @Test
    public void testGenerateIdCreates10CharID() {
        String id = SessionIdGenerator.getId();
        Assert.assertNotNull(id);
        Assert.assertEquals(10, id.length());
    }

    @Test
    public void testSequentiallyGeneratedIdsAreNotIdentical() {
        String id1 = SessionIdGenerator.getId();
        String id2 = SessionIdGenerator.getId();

        Assert.assertNotNull(id1);
        Assert.assertNotNull(id2);

        Assert.assertFalse(id1.equals(id2));
    }
}

/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//digester/src/test/org/apache/commons/digester/plugins/TestRuleInfo.java,v 1.2 2003/10/05 15:30:03 rdonkin Exp $
 * $Revision: 1.2 $
 * $Date: 2003/10/05 15:30:03 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Apache", "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache" nor may "Apache" appear in their names without prior 
 *    written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */ 


package org.apache.commons.digester.plugins;

import java.util.List;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.digester.*;
import org.apache.commons.digester.plugins.*;

/**
 * Test cases for the declaration of custom rules for a plugin using
 * a separate class to define the rules.
 */

public class TestRuleInfo extends TestCase {
    /** Standard constructor */
    public TestRuleInfo(String name) { 
        super(name);
    }

    /** Set up instance variables required by this test case. */
    public void setUp() {}

    /** Return the tests included in this test suite. */
    public static Test suite() {

        return (new TestSuite(TestRuleInfo.class));

    }

    /** Tear down instance variables required by this test case.*/
    public void tearDown() {}
        
    // --------------------------------------------------------------- Test cases

    public void testRuleInfoExplicitClass() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class by explicitly declaring the rule class.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5a.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, label should
        assertEquals("anonymous", label.getId());
        assertEquals("std label", label.getLabel());
    }
    
    public void testRuleInfoExplicitMethod() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class by explicitly declaring the rule class.
        //   and explicitly declaring the rule method name.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5b.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, altlabel should
        assertEquals("anonymous", label.getId());
        assertEquals("alt label", label.getLabel());
    }
    
    public void testRuleInfoAutoDetect() throws Exception {
        // * tests that custom rules can be declared on a 
        //   separate class with name {plugin-class}RuleInfo,
        //   and they are automatically detected and loaded.

        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);
        
        PluginDeclarationRule pdr = new PluginDeclarationRule();
        digester.addRule("root/plugin", pdr);
        
        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);
        
        try {
            digester.parse(
                TestAll.getInputStream(this, "test5c.xml"));
        }
        catch(Exception e) {
            throw e;
        }

        Object child;
        List children = root.getChildren();
        assertTrue(children != null);
        assertEquals(1, children.size());
        
        child = children.get(0);
        assertTrue(child != null);
        assertEquals(TextLabel2.class, child.getClass());
        TextLabel2 label = (TextLabel2) child;
        
        // id should not be mapped, label should
        assertEquals("anonymous", label.getId());
        assertEquals("std label", label.getLabel());
    }
}

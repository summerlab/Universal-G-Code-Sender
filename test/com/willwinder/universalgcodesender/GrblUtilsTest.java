/*
    Copywrite 2013 Will Winder

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.willwinder.universalgcodesender;

import com.willwinder.universalgcodesender.GrblUtils.Capabilities;
import javax.vecmath.Point3d;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author wwinder
 */
public class GrblUtilsTest {
    /**
     * Test of removeComment method, of class GrblUtils.
     */
    @Test
    public void testRemoveComment() {
        System.out.println("removeComment");
        String command;
        String expResult;
        String result;

        command   = "some command ;comment";
        expResult = "some command";
        result = GrblUtils.removeComment(command);
        assertEquals(expResult, result);

        command   = "some (comment here) command ;comment";
        expResult = "some  command";
        result = GrblUtils.removeComment(command);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseComment method, of class GrblUtils.
     */
    @Test
    public void testParseComment() {
        System.out.println("parseComment");
        String command;
        String expResult;
        String result;
        
        command   = "some command ;comment";
        expResult = "comment";
        result = GrblUtils.parseComment(command);
        assertEquals(expResult, result);
        
        command   = "some (comment here) command ;comment";
        expResult = "comment here";
        result = GrblUtils.parseComment(command);
        assertEquals(expResult, result);
    }

    /**
     * Test of isGrblVersionString method, of class GrblUtils.
     */
    @Test
    public void testIsGrblVersionString() {
        System.out.println("isGrblVersionString");
        String response;
        Boolean expResult;
        Boolean result;
        
        response = "Grbl 0.8c";
        expResult = true;
        result = GrblUtils.isGrblVersionString(response);
        assertEquals(expResult, result);

        response = "blah 0.8c";
        expResult = false;
        result = GrblUtils.isGrblVersionString(response);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersionDouble method, of class GrblUtils.
     */
    @Test
    public void testGetVersionDouble() {
        System.out.println("getVersionDouble");
        String response;
        double expResult;
        double result;

        response = "Grbl 0.8c";
        expResult = 0.8;
        result = GrblUtils.getVersionDouble(response);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getVersionLetter method, of class GrblUtils.
     */
    @Test
    public void testGetVersionLetter() {
        System.out.println("getVersionLetter");
        String response = "Grbl 0.8c";
        String expResult = "c";
        String result = GrblUtils.getVersionLetter(response);
        assertEquals(expResult, result);
    }

    /**
     * Test of isRealTimeCapable method, of class GrblUtils.
     */
    @Test
    public void testIsRealTimeCapable() {
        System.out.println("isRealTimeCapable");
        double version;
        Boolean expResult;
        Boolean result;

        version = 0.8;
        expResult = true;
        result = GrblUtils.isRealTimeCapable(version);
        assertEquals(expResult, result);

        version = 0.7;
        expResult = false;
        result = GrblUtils.isRealTimeCapable(version);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGrblStatusCapabilities method, of class GrblUtils.
     */
    @Test
    public void testGetGrblStatusCapabilities() {
        System.out.println("getGrblStatusCapabilities");
        double version;
        String letter;
        Capabilities expResult;
        Capabilities result;

        version = 0.8;
        letter = "c";
        expResult = Capabilities.STATUS_C;
        result = GrblUtils.getGrblStatusCapabilities(version, letter);
        assertEquals(expResult, result);
        
        version = 0.8;
        letter = "a";
        expResult = null;
        result = GrblUtils.getGrblStatusCapabilities(version, letter);
        assertEquals(expResult, result);
    }

    /**
     * Test of isGrblStatusString method, of class GrblUtils.
     */
    @Test
    public void testIsGrblStatusString() {
        System.out.println("isGrblStatusString");
        String response;
        Boolean expResult;
        Boolean result;
        
        response = "<position string is in angle brackets...>";
        expResult = true;
        result = GrblUtils.isGrblStatusString(response);
        assertEquals(expResult, result);
        
        response = "blah";
        expResult = false;
        result = GrblUtils.isGrblStatusString(response);
        assertEquals(expResult, result);
    }

    /**
     * Test of getStateFromStatusString method, of class GrblUtils.
     */
    @Test
    public void testGetStateFromStatusString() {
        System.out.println("getStateFromStatusString");
        String status;
        Capabilities version;
        String expResult;
        String result;

        status = "<Idle,MPos:5.529,0.560,7.000,WPos:1.529,-5.440,-0.000>";
        version = Capabilities.STATUS_C;
        expResult = "Idle";
        result = GrblUtils.getStateFromStatusString(status, version);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMachinePositionFromStatusString method, of class GrblUtils.
     */
    @Test
    public void testGetMachinePositionFromStatusString() {
        System.out.println("getMachinePositionFromStatusString");
        String status = "<Idle,MPos:5.529,0.560,7.000,WPos:1.529,-5.440,-0.000>";
        Capabilities version = Capabilities.STATUS_C;
        Point3d expResult = new Point3d(5.529, 0.560, 7.000);
        Point3d result = GrblUtils.getMachinePositionFromStatusString(status, version);
        assertEquals(expResult, result);
    }

    /**
     * Test of getWorkPositionFromStatusString method, of class GrblUtils.
     */
    @Test
    public void testGetWorkPositionFromStatusString() {
        System.out.println("getWorkPositionFromStatusString");
        String status = "<Idle,MPos:5.529,0.560,7.000,WPos:1.529,-5.440,-0.000>";
        Capabilities version = Capabilities.STATUS_C;
        Point3d expResult = new Point3d(1.529, -5.440, -0.000);
        Point3d result = GrblUtils.getWorkPositionFromStatusString(status, version);
        assertEquals(expResult, result);
    }
}

/*
 * **
 *  *                                        __          ____                                     __
 *  *     /'\_/`\                 __        /\ \        /\  _`\                                __/\ \__
 *  *    /\      \  __  __   ___ /\_\    ___\ \ \___    \ \,\L\_\     __    ___  __  __  _ __ /\_\ \ ,_\  __  __
 *  *    \ \ \__\ \/\ \/\ \/' _ `\/\ \  /'___\ \  _ `\   \/_\__ \   /'__`\ /'___\\ \/\ \/\`'__\/\ \ \ \/ /\ \/\ \
 *  *     \ \ \_/\ \ \ \_\ \\ \/\ \ \ \/\ \__/\ \ \ \ \    /\ \L\ \/\  __//\ \__/ \ \_\ \ \ \/ \ \ \ \ \_\ \ \_\ \
 *  *      \ \_\\ \_\ \____/ \_\ \_\ \_\ \____\\ \_\ \_\   \ `\____\ \____\ \____\ \____/\ \_\  \ \_\ \__\\/`____ \
 *  *       \/_/ \/_/\/___/ \/_/\/_/\/_/\/____/ \/_/\/_/    \/_____/\/____/\/____/\/___/  \/_/   \/_/\/__/ `/___/> \
 *  *                                                                                                         /\___/
 *  *                                                                                                         \/__/
 *  *
 *  *     ____                                               __          ____
 *  *    /\  _`\                                            /\ \        /\  _`\
 *  *    \ \ \L\ \     __    ____    __     __     _ __  ___\ \ \___    \ \ \L\_\  _ __  ___   __  __  _____
 *  *     \ \ ,  /   /'__`\ /',__\ /'__`\ /'__`\  /\`'__\'___\ \  _ `\   \ \ \L_L /\`'__\ __`\/\ \/\ \/\ '__`\
 *  *      \ \ \\ \ /\  __//\__, `\\  __//\ \L\.\_\ \ \/\ \__/\ \ \ \ \   \ \ \/, \ \ \/\ \L\ \ \ \_\ \ \ \L\ \
 *  *       \ \_\ \_\ \____\/\____/ \____\ \__/.\_\\ \_\ \____\\ \_\ \_\   \ \____/\ \_\ \____/\ \____/\ \ ,__/
 *  *        \/_/\/ /\/____/\/___/ \/____/\/__/\/_/ \/_/\/____/ \/_/\/_/    \/___/  \/_/\/___/  \/___/  \ \ \/
 *  *                                                                                                    \ \_\
 *  *    This file is part of BREW.
 *  *
 *  *    BREW is free software: you can redistribute it and/or modify
 *  *    it under the terms of the GNU General Public License as published by
 *  *    the Free Software Foundation, either version 3 of the License, or
 *  *    (at your option) any later version.
 *  *
 *  *    BREW is distributed in the hope that it will be useful,
 *  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *    GNU General Public License for more details.
 *  *
 *  *    You should have received a copy of the GNU General Public License
 *  *    along with BREW.  If not, see <http://www.gnu.org/licenses/>.                                                                                                  \/_/
 *
 */

package edu.hm.muse.controller;

import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Types;
import java.util.List;

@Controller
public class Logincontroller {

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public ModelAndView showLoginScreen() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", "Bitte geben Sie ihren Username und ihr Passwort ein.");
        return mv;
    }



    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ModelAndView doSomeLogin(@RequestParam(value = "mname", required = false) String mname, @RequestParam(value = "mpwd", required = false) String mpwd, HttpSession session) {
    	ModelAndView mv = new ModelAndView("picturegallary");
    	if(validateLogin(mname, mpwd)){
        	mv = new ModelAndView("profilpage");
        	setSessionForUser(mname, session);
        	
    	}
    	HttpSession sesseion = session;
    //	jdbcTemplate.execute(sql);  
    	return mv;
    }

    private boolean setSessionForUser(String mname, HttpSession session) {
		String sql = "UPDATE M_USER SET sessionID = '" + session.getId() + "' WHERE muname ='" + mname + "'";
		try {
			jdbcTemplate.execute(sql);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	private boolean validateUserName(String username){
    	String sql = "SELECT COUNT(*) FROM M_USER WHERE muname='" + username +"'";
    	boolean userExists = jdbcTemplate.queryForInt(sql) == 1 ? true : false;
    	return userExists;
    }
    
    private boolean validateLogin(String mname, String mpwd){
    	if(!validateUserName(mname)){
    		return false;
    	}
    	if(validatePW(mname, mpwd)){
    		return true;
    	}
    	return false;
    }

	private boolean validatePW(String mname, String mpwd) {
		String sql = "SELECT mpwd from M_USER WHERE muname='" + mname +"'";
	    List<String> results = jdbcTemplate.queryForList(sql, String.class); 
	    String pwd = results.get(0);
	    if(pwd.equals(mpwd)){
	    	return true;
	    }
	    return false;
	}

}

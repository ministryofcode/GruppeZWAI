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

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LookupUserController {
	
	 private JdbcTemplate jdbcTemplate;
	 private int userID;

	    @Resource(name = "dataSource")
	    public void setDataSource(DataSource dataSource) {
	        jdbcTemplate = new JdbcTemplate(dataSource);
	    }

	    @RequestMapping(value = "/lookupuser.htm", method = RequestMethod.GET)	    
	    public ModelAndView lookupuser(HttpSession session) {
	    	try {
				String sql = "SELECT * FROM M_USER WHERE sessionID = '" + session.getId() + "';";
				Map<String,?> userdata = jdbcTemplate.queryForMap(sql);
				ModelAndView mv = new ModelAndView("lookupuser");
			    mv.addObject("msg", "Bitte geben Sie den gesuchten Benutzernamen ein.");
				return mv;
			} catch (DataAccessException e) {
				return new ModelAndView("loginfalse");
			}
		}
	    
	    @RequestMapping(value = "/lookupuser2.htm", method = RequestMethod.GET)	    
	    public ModelAndView lookupuser2(HttpSession session) {
	    	try {
				String sql = "SELECT * FROM M_USER WHERE sessionID = '" + session.getId() + "';";
				Map<String,?> userdata = jdbcTemplate.queryForMap(sql);
				ModelAndView mv = new ModelAndView("lookupuser");
			    mv.addObject("msg", "Bitte geben Sie den gesuchten Benutzernamen ein.");
				return mv;
			} catch (DataAccessException e) {
				return new ModelAndView("loginfalse");
			}
		}
	    
	    @RequestMapping(value = "/lookupuser.htm", method = RequestMethod.POST)
	    public ModelAndView doSomeLogin(@RequestParam(value = "mname", required = false) String mname, HttpSession session) {
	    	ModelAndView mv = new ModelAndView("lookupuser");
	    	mv.addObject("msg", "Ein Benutzer mit dem Namen '"+ mname +"' existiert nicht.");
	    	if(validateUserName(mname)){
	    		mv = new ModelAndView("lookupuser2");
	    		mv.addObject("msg", "Benutzer '"+ mname +"' gefunden!");
	    		mv.addObject("id", getID(mname));
	    	}
	    	return mv;
	    }
	    
	    private boolean validateUserName(String username){
	    	String sql = "SELECT COUNT(*) FROM M_USER WHERE muname='" + username +"'";
	    	boolean userExists = jdbcTemplate.queryForInt(sql) == 1 ? true : false;
	    	return userExists;
	    }
	    private int getID(String username){
	    	String sql = "SELECT * FROM M_USER WHERE muname='" + username +"'";
	    	Map<String,?> userdata = jdbcTemplate.queryForMap(sql);
	    	userID = (Integer)userdata.get("ID");
	    	return userID;
	    }
	    
}

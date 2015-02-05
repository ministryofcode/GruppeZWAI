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
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class RegisterController {
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @RequestMapping(value = "/register.htm", method = RequestMethod.GET)
    public ModelAndView showLoginScreen() {
        ModelAndView mv = new ModelAndView("register");
        mv.addObject("msg", "Benutzername und Passwort eingeben:");
        return mv;
    }
    @RequestMapping(value = "/register.htm", method = RequestMethod.POST)
    public ModelAndView doSomeLogin(@RequestParam(value = "mname", required = true) String mname, 
    								@RequestParam(value = "mpwd", required = true) String mpwd,
    								@RequestParam(value = "choosepicture", required = false) String pic,
    								HttpSession session) {
        ModelAndView mv = new ModelAndView("register");
        
        String sqlUsername = "Select Count(*) from M_USER where muname='"+ mname+"'";
        int countUser = jdbcTemplate.queryForInt(sqlUsername);
        
        if (countUser == 0){
        //Set PicName
        if (pic == null) {
				pic = "pic1";
			} else if (pic.equals("Bild 1")) {
				pic = "pic1";
			} else if (pic.equals("Bild 2")) {
				pic = "pic2";
			} else if (pic.equals("Bild 3")) {
				pic = "pic3";
			} else if (pic.equals("Bild 4")) {
				pic = "pic4";
			}

                      
        // Add next User to Database
        String sql = "INSERT INTO M_USER (ID, muname, mpwd, picName) VALUES (NULL, '" + mname + "', '" + mpwd + "', '" + pic + "');";    	
        jdbcTemplate.execute(sql);
        
        String sqlSelect = "SELECT COUNT(*) FROM M_USER;";        
        final int count = jdbcTemplate.queryForInt(sqlSelect);
        
        mv.addObject("msg", "Benutzer " + mname + " mit ID: " + count + " wurde angelegt");
        
        }else {
        	mv.addObject("msg", "Benutzer mit dem Namen "+ mname+" bereits vorhanden!");
        }
        return mv;
    }
}

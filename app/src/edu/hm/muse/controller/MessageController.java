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

import java.util.ArrayList;
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
public class MessageController {
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@RequestMapping(value = "/newMessage.htm", method = RequestMethod.GET)
	public ModelAndView showLoginScreen() {
		ModelAndView mv = new ModelAndView("newMessage");
		return mv;
	}

	@RequestMapping(value = "/newMessage.htm", method = RequestMethod.POST)
	public ModelAndView doSomeLogin(
			@RequestParam(value = "Receiver", required = true) String receiver,
			@RequestParam(value = "Nachricht", required = true) String message,
			@RequestParam(value = "chooseType", required = true) String type,
			HttpSession session) {

		String senderID = getUserIDForSessionID(session.getId());

		String sql = "SELECT ID FROM M_USER WHERE muname ='" + receiver + "'";
		String receiverID = jdbcTemplate.queryForObject(sql, String.class);

		String feedback = "0";
		if (type.equals("MIT Empfangsbestaetigung Senden (nur für Freunde)")) {
			feedback = "1";
		}

		String insert = "INSERT INTO M_MESSAGES(S_ID, R_ID , Message, Feedback, READ) VALUES ("
				+ senderID
				+ ","
				+ receiverID
				+ ",'"
				+ message
				+ "',"
				+ feedback + ", 0)";
		System.out.println(insert);
		jdbcTemplate.execute(insert);

		return null;
	}

	private String getUserIDForSessionID(String id) {
		String sql = "SELECT ID FROM M_USER WHERE sessionID ='" + id + "'";
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	@RequestMapping(value = "/unreadMessages.htm", method = RequestMethod.GET)
	public ModelAndView showUnreadMessages() {
		ModelAndView mv = new ModelAndView("unreadMessages");
		return mv;
	}

	@RequestMapping(value = "/readMessages.htm", method = RequestMethod.GET)
	public ModelAndView showReadMessages() {
		ModelAndView mv = new ModelAndView("readMessages");
		return mv;
	}

	@RequestMapping(value = "/unreadMessages.htm", method = RequestMethod.POST)
	public ModelAndView showUnread(HttpSession session) {

		ModelAndView mv = new ModelAndView("unreadMessages");
		mv.addObject("unreadMessages", doSomeMagic(false, session));
		return mv;
	}
	
	@RequestMapping(value = "/readMessages.htm", method = RequestMethod.POST)
	public ModelAndView showRead(HttpSession session) {

		ModelAndView mv = new ModelAndView("readMessages");
		mv.addObject("readMessages", doSomeMagic(true, session));
		return mv;
	}

	private List<String> doSomeMagic(boolean read, HttpSession session) {
		List<String> finalMessages = new ArrayList<String>();

		String receiverID = getUserIDForSessionID(session.getId());

		String sql = "select ID from M_Messages where R_ID=" + receiverID
				+ " AND READ = 0";

		if (read) {
			sql = "select ID from M_Messages where R_ID=" + receiverID
					+ " AND READ = 1";
		}

		List<Integer> messageID = new ArrayList<Integer>(
				jdbcTemplate.queryForList(sql, Integer.class));

		for (int i = 0; i < messageID.size(); i++) {
			Object obj = messageID.get(i);
			String messageIDasStr = obj.toString();

			String sql2 = "SELECT S_ID FROM M_Messages WHERE ID="
					+ messageIDasStr;
			String sql3 = "SELECT message FROM M_Messages WHERE ID="
					+ messageIDasStr;

			String senderID = jdbcTemplate.queryForObject(sql2, String.class);
			String wizzardOfOz = "SELECT muname FROM M_USER where ID="
					+ senderID;
			String sender = jdbcTemplate.queryForObject(wizzardOfOz,
					String.class);

			wizzardOfOz = "SELECT muname FROM M_USER where ID=" + receiverID;
			String receiver = jdbcTemplate.queryForObject(wizzardOfOz,
					String.class);

			String message = jdbcTemplate.queryForObject(sql3, String.class);

			String finalMessageWithAuthor = sender + " hat geschrieben: "
					+ message;
			finalMessages.add(finalMessageWithAuthor);

			String sqlUpdate = "UPDATE M_MESSAGES SET Read = 1 WHERE ID ="
					+ messageIDasStr;
			jdbcTemplate.execute(sqlUpdate);

			String sqlFriends = "SELECT COUNT(*) FROM M_Friends WHERE ID ="
					+ receiverID + " AND U_ID =" + senderID;
			if (jdbcTemplate.queryForInt(sqlFriends) > 0 && !read) {
				String feedbackMessage = receiver
						+ " hat deine Nachricht gelesen.";
				String sqlReturnMassage = "INSERT INTO M_MESSAGES(S_ID, R_ID , Message, Feedback, READ) VALUES ("
						+ receiverID
						+ ","
						+ senderID
						+ ",'"
						+ feedbackMessage
						+ "'," + "0" + ", 0)";

				String sqlFeedback = "SELECT feedback FROM M_Messages WHERE ID="
						+ messageIDasStr;
				String feedback = jdbcTemplate.queryForObject(sqlFeedback,
						String.class);

				if (feedback.equals("TRUE")) {

					jdbcTemplate.execute(sqlReturnMassage);
				} else {
					System.out.println("keine bestätigung erwünsch");
				}
			}
		}
		return finalMessages;
	}
}

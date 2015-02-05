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
public class ProfilpageController {
	
	 private JdbcTemplate jdbcTemplate;
	 private int userID, loggedID;

	    @Resource(name = "dataSource")
	    public void setDataSource(DataSource dataSource) {
	        jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	    
	    private List<Map<String,Object>> getPosts(Integer uid, Integer lid) {
	    	
			String sqlPosts = "SELECT * FROM M_POSTS WHERE U_ID = " + uid + ";";
			String sqlLikes = "SELECT * FROM M_LIKES;";
			String sqlFriends = "SELECT * FROM M_FRIENDS WHERE U_ID =" + lid + ";";
			
			List<Map<String,Object>> posts = jdbcTemplate.queryForList(sqlPosts);
			List<Map<String,Object>> likes = jdbcTemplate.queryForList(sqlLikes);
			List<Map<String,Object>> friends = jdbcTemplate.queryForList(sqlFriends);
			
			int likeCount;
			int dislikeCount;
			boolean isFriend = false;
			
			for(Map<String,Object> friend: friends)
			{
				if((Integer)friend.get("U_ID") == lid)
				{
					isFriend = true;
				}
			}
			
		if(uid == lid || uid != lid && isFriend) 
			{
				for(Map<String,Object> post: posts) 
				{
					likeCount = 0;
					dislikeCount = 0;

					for(Map<String,Object> like: likes) 
					{
						String p_ID = like.get("P_ID").toString();
						String id = post.get("ID").toString();
												
						if(p_ID.equals(id))
						{
							if((boolean)like.get("likestatus"))
								likeCount++;
							else
								dislikeCount++;
						}
					}
					
					post.put("likes", likeCount);
					post.put("dislikes", dislikeCount);
				}
			}
			else
			{
				for(Map<String,Object> post: posts) 
				{
					likeCount = 0;
					dislikeCount = 0;

//					if(Boolean.getBoolean(post.get("private").toString())) {
//						posts.remove(post);
//						continue;
//					}
					
					for(Map<String,Object> like: likes) 
					{
						if(Integer.getInteger(like.get("P_ID").toString()) == Integer.getInteger(post.get("ID").toString()))
						{
							if((boolean)like.get("likestatus"))
								likeCount++;
							else
								dislikeCount++;
						}
					}
					
					post.put("likes", likeCount);
					post.put("dislikes", dislikeCount);
				}
			}			
 	
	    	return posts;
	    }

	    @RequestMapping(value = "/profilpage.htm", method = RequestMethod.GET)
	    public ModelAndView profilpage(@RequestParam(value="user", required=false) Integer reqUser, 
	    							   HttpSession session) {    	
	        ModelAndView mv;
	        String sqlLogged;
	        String sqlUser;
	        
	        if(reqUser != null && reqUser.intValue() != 0) 
	        {
	        	sqlLogged = "SELECT ID FROM M_USER WHERE sessionID = '" + session.getId() + "';";
	        	sqlUser = "SELECT * FROM M_USER WHERE ID = '" + reqUser.intValue() + "';";	        	
	        	mv = new ModelAndView("profilpageadd");
	        } 
	        else 
	        {
	        	sqlLogged = "SELECT ID FROM M_USER WHERE sessionID = '" + session.getId() + "';";
	        	sqlUser = "SELECT * FROM M_USER WHERE sessionID = '" + session.getId() + "';";	        	
	        	mv = new ModelAndView("profilpage");
	        }
	        	        
	        try 
	        {
				Map<String,?> userdata = jdbcTemplate.queryForMap(sqlUser);
				Map<String,?> loggeddata = jdbcTemplate.queryForMap(sqlLogged);				
				
				userID = (Integer) userdata.get("ID");	
				loggedID = (Integer) loggeddata.get("ID");
				
				List<Map<String,Object>> postsWithLikes = getPosts(userID, loggedID);

				mv.addObject("user", userdata.get("muname"));
				mv.addObject("loggedID", loggeddata.get("ID"));
				mv.addObject("userID", userdata.get("ID"));
				mv.addObject("pictures", userdata.get("picName"));
				mv.addObject("posts", postsWithLikes);
				return mv;
			} 
	        catch (DataAccessException e) 
	        {
	        	System.out.println(e.getMessage());
				return new ModelAndView("loginfalse");
			}
	    }
	    
	    @RequestMapping(value = "/profilpage.htm", method = RequestMethod.POST)
	    public String profilpage(@RequestParam(value = "post", required = false) String post,
	    						 @RequestParam(value = "privacy", required = false) boolean privacy, 
	    						 @RequestParam(value = "userID", required = false) Integer likingUser,
	    						 @RequestParam(value = "postID", required = false) Integer likedPost,
	    						 @RequestParam(value = "TRUE", required = false) Object likeButton,
	    						 @RequestParam(value = "FALSE", required = false) Object dislikeButton,
	    						 @RequestParam(value = "senderID", required = false) Integer senderID,
	    						 @RequestParam(value = "receiverID", required = false) Integer receiverID,
	    						 HttpSession session) {  
	    	if(post != null)
	    	{
	    		String sql = "INSERT INTO M_POSTS (ID, U_ID, message, private) VALUES (NULL, " + userID + ", '" + post + "', " + privacy + ");";
		        
		        jdbcTemplate.execute(sql);
	    	}
	    	else if(senderID != null && receiverID != null)
	    	{
	    		String sql = "INSERT INTO M_FRIENDS (ID, U_ID) VALUES (" + senderID.intValue() + ", " + receiverID.intValue() + ");";
		    	jdbcTemplate.execute(sql);
	    	}
	    	else
	    	{
	    		final boolean like;
		        if(likeButton == null)
		        {
		        	like = false;
		        }
		        else
		        {
		        	like = true;
		        }
		        
		        if(likingUser != null && likedPost != null)
		        {
		        	String sqlLike = "INSERT INTO M_LIKES (ID, U_ID, P_ID, likestatus) VALUES "
		        				   + "(NULL, "+ likingUser.intValue() + ", "+ likedPost.intValue() + ", "+ like + ");";
		        	
		        	jdbcTemplate.execute(sqlLike);
		        }
	    	}	    	
	    	
	    	return "redirect:/profilpage.htm";
	    }
	    
//	    @RequestMapping(value = "/profilpageadd.htm", method = RequestMethod.POST)
//	    public String profilpage(@RequestParam(value = "senderID", required = false) Integer senderID,
//	    						 @RequestParam(value = "receiverID", required = false) Integer receiverID,
//	    						 HttpSession session) {   
//	    	String sql = "INSERT INTO M_FRIENDS (ID, U_ID) VALUES (" + senderID.intValue() + ", " + receiverID.intValue() + ");";
//	    	jdbcTemplate.execute(sql);
//	    	
//	    	return "redirect:/profilpage.htm?user="+ receiverID.intValue();
//	    }

}

//test1234

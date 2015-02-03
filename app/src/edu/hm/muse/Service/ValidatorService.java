package edu.hm.muse.Service;

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


public class ValidatorService {

	private JdbcTemplate jdbcTemplate;
	private String sessionID;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ValidatorService(String sessionID){
		this.sessionID = sessionID;
	}
	
	public boolean isValid(){
		if(sessionID == null){
			return false;
		}
		String sql = "SELECT COUNT(*) FROM M_USER WHERE sessionID='" + this.sessionID +"'";
    	boolean valid = jdbcTemplate.queryForInt(sql) == 1 ? true : false;
    	return valid;		
	}
}

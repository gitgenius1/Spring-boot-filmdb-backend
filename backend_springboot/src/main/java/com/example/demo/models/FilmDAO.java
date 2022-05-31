package com.example.demo.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class FilmDAO {

	Film oneFilm = null;

	Connection conn = null;

	Statement stmt = null;

	String user = "sharplui";

	String password = "potrenkL7";

	// Note none default port used, 6306 not 3306

	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public FilmDAO() {
	}

	private void openConnection() {

		// loading jdbc driver for mysql

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database

		try {

			// connection string for demos database, username demos, password demos

			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();

		} catch (SQLException se) {
			System.out.println(se);
		}

	}

	private void closeConnection() {

		try {

			conn.close();

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	private Film getNextFilm(ResultSet rs) {

		Film thisFilm = null;

		try {

			thisFilm = new Film(

					rs.getInt("id"),

					rs.getString("title"),

					rs.getInt("year"),

					rs.getString("director"),

					rs.getString("stars"),

					rs.getString("review"));

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return thisFilm;
	}

//-----------------------------------------------------------------------------------

	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> allFilms = new ArrayList<Film>();

		openConnection();

		// Create select statement and execute it

		try {

			String selectSQL = "select * from films";

			ResultSet rs1 = stmt.executeQuery(selectSQL);

			// Retrieve the results

			while (rs1.next()) {

				oneFilm = getNextFilm(rs1);

				allFilms.add(oneFilm);

			}

			 stmt.close();

			 closeConnection();

		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;

	}

	public Film getFilmByID(int id) {

		openConnection();

		oneFilm = null;

			// Create select statement and execute it

		try {

			String selectSQL = "select * from films where id=" + id;

			ResultSet rs1 = stmt.executeQuery(selectSQL);

			// Retrieve the results

			while (rs1.next()) {

				oneFilm = getNextFilm(rs1);

			}

			stmt.close();

			closeConnection();

		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneFilm;

	}

	public String insertFilm(Film film) {

		openConnection();

			// Create insert statement and execute it, getting the required attributes from
			// the passed in params

		try {

			StringBuilder insert = new StringBuilder();

			insert.append(film.getId() + ",");
			insert.append("'" + film.getTitle() + "'" + ",");
			insert.append(film.getYear() + ",");
			insert.append("'" + film.getDirector() + "'" + ",");
			insert.append("'" + film.getStars() + "'" + ",");
			insert.append("'" + film.getReview() + "'");

			String insertSQL = "insert into films (id,title,year,director,stars,review) values(" + insert + ");";

			stmt.executeUpdate(insertSQL);

			closeConnection();
			
			return "Successfully Inserted";

		} catch (SQLException se) {

			System.out.println(se);
			System.out.println("Error");
			
			return "Error";

		}
	}

	public String updateFilm(Film film) {

		openConnection();

		// Create a statement and execute it, getting the required attributes from the
		// passed in params

		try {

			StringBuilder values = new StringBuilder("update films SET ");

			values.append("title = '" + film.getTitle() + "'" + ",");
			values.append("year = '" + film.getYear() + "'" + ",");
			values.append("director = '" + film.getDirector() + "'" + ",");
			values.append("stars = '" + film.getStars() + "'" + ",");
			values.append("review = '" + film.getReview() + "'");
			values.append("where id = '" + film.getId() + "';");

			stmt.executeUpdate(values.toString());

			closeConnection();
			
			return "Successfully updated";

		} catch (SQLException se) {

			System.out.println(se);
			System.out.println("error");
			
			return "Error";
		}
	}

	public String deleteFilm(int id) {

		openConnection();

			// Create a delete statement and execute it

		try {

			String deleteSQL = "delete from films where id=" + id;

			stmt.executeUpdate(deleteSQL);

			closeConnection();
			
			return "Succesfully deleted";

		} catch (SQLException se) {

			System.out.println(se);
			System.out.println("Error");
			return "error";

		}

	}

	public ArrayList<Film> searchFilm(String searchStr) {

		ArrayList<Film> allfilms = new ArrayList<Film>();

		openConnection();
		
			// Query db by string title  

		try {

			String selectSQL = "select * from films where title like '%" + searchStr + "%'";

			ResultSet rs1 = stmt.executeQuery(selectSQL);

			while (rs1.next()) {

				oneFilm = getNextFilm(rs1);

				allfilms.add(oneFilm);

			}

		} catch (SQLException se) {

			System.out.println(se);

		}

		return allfilms;

	}
}

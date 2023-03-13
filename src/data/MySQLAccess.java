package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import controller.Controller;
import model.Answer;
import model.Party;
import model.Player;
import model.Question;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	private Controller monController;

	private String urlCnx;
	private String loginCnx;
	private String passwordCnx;

	public MySQLAccess(Controller unController) {
		this.monController = unController;

		// Charge le fichier de propriété contenant les informations d'accès à la BDD
		Properties properties = new Properties();
		try (InputStream fis = getClass().getClassLoader().getResourceAsStream("data/conf.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Charge le driver
		try {
			Class.forName(properties.getProperty("jdbc.driver.class"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Crée la connexion
		urlCnx = properties.getProperty("jdbc.url");
		loginCnx = properties.getProperty("jdbc.login");
		passwordCnx = properties.getProperty("jdbc.password");

	}

	public int nombreTotalQuestion() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {
			ResultSet resultSet = st.executeQuery("select count(*) from question");
			resultSet.next();
			int res = resultSet.getInt(1);
			return res;
		}
	}

	private ArrayList<Answer> getAnswersFromQuestion(Connection connection, int id)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Answer> answers = new ArrayList<Answer>();

		String strSqlQuestion = "select * from answer where id_question = " + id + " order by rand()";

		try (Statement st = connection.createStatement()) {
			ResultSet resultSet = st.executeQuery(strSqlQuestion);
			for (int i = 0; i < 4; i++) {
				resultSet.next();
				String indexA = Integer.toString(i + 1);
				String descA = resultSet.getString(1);
				Boolean resA = resultSet.getBoolean(2);
				answers.add(new Answer(indexA, descA, resA));
			}
			return answers;
		}

	}

	public ArrayList<Question> getQuestions(ArrayList<Integer> listeIdQuestion)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Question> questions = new ArrayList<Question>();

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {

			for (int i : listeIdQuestion) {
				ResultSet resultSet = st.executeQuery("select * from question where id_question = " + i);
				resultSet.next();

				int idQuestion = resultSet.getInt(1);
				String nomQuestion = resultSet.getString(2);

				questions.add(new Question(idQuestion, nomQuestion, getAnswersFromQuestion(connection, idQuestion)));
			}
		}
		return questions;
	}

	public Party createParty(Party theParty) {
		String query = "INSERT INTO h5ws00fg4ypyuohr.GAME VALUES (DEFAULT, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);

				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, theParty.getName());
			ps.setInt(2, theParty.getIdLeader());
			ps.setInt(3, 1);
			ps.setString(4, "12:00:00");

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

			theParty.setIdParty(generatedKey);
			return theParty;

		} catch (SQLException e) {
			e.printStackTrace();
			return theParty;
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void createQuestionParty(Party theParty) {
		String query = "INSERT INTO h5ws00fg4ypyuohr.GAME_QUESTION VALUES (?, ?)";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement ps = connection.prepareStatement(query);) {

			for (Question question : theParty.getGroupQuestions()) {

				ps.setInt(1, theParty.getIdParty());
				ps.setInt(2, question.getId());

				ps.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public void createPlayerParty(Party theParty) {
		String query = "INSERT INTO h5ws00fg4ypyuohr.GAME_PLAYER VALUES (?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement ps = connection.prepareStatement(query);) {

			ps.setInt(1, theParty.getIdParty());
			ps.setInt(2, theParty.getIdLeader());
			ps.setInt(3, 0);

			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public ArrayList<Party> getListParty() {
		String query = "SELECT game.name_game, game.id_game, count(*) as nbQuestion_game, game.start_game\r\n"
				+ "FROM h5ws00fg4ypyuohr.game\r\n"
				+ "inner join game_question on game_question.id_game = game.id_game\r\n"
				+ "where game.progress_game = 1\r\n" + "group by game.id_game, game.name_game, game.start_game\r\n"
				+ "order by game.id_game desc;";
		// + "order by game.name_game;";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				Statement st = connection.createStatement()) {

			ArrayList<Party> lesParty = new ArrayList<Party>();

			ResultSet res = st.executeQuery(query);
			while (res.next()) {
				lesParty.add(new Party(res.getString(1), res.getInt(2), res.getInt(3), res.getString(4)));
			}

			return lesParty;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}

}
package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import controller.Controller;
import model.Answer;
import model.Game;
import model.Player;
import model.Question;

public class MySQLAccess {

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
		urlCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.url"));
		loginCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.login"));
		passwordCnx = monController.getTheDecrypter().decrypt(properties.getProperty("jdbc.password"));
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

//	private ArrayList<Answer> getAnswersFromQuestion(Connection connection, int id)
//			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
//
//		ArrayList<Answer> answers = new ArrayList<Answer>();
//		System.out.println(id);
//		String strSqlQuestion = "select * from answer where id_question = " + id + " order by rand()";
//
//		try (Statement st = connection.createStatement()) {
//			ResultSet resultSet = st.executeQuery(strSqlQuestion);
//			for (int i = 0; i < 4; i++) {
//				resultSet.next();
//				String indexA = Integer.toString(i + 1);
//				String descA = resultSet.getString(2);
//				Boolean resA = resultSet.getBoolean(3);
//				answers.add(new Answer(indexA, descA, resA));
//			}
//			return answers;
//		}
//
//	}
//
//	public ArrayList<Question> getQuestions(ArrayList<Integer> listeIdQuestion)
//			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
//
//		ArrayList<Question> questions = new ArrayList<Question>();
//
//		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
//				Statement st = connection.createStatement()) {
//
//			for (int i : listeIdQuestion) {
//				ResultSet resultSet = st.executeQuery("select * from question where id_question = " + i);
//				resultSet.next();
//
//				int idQuestion = resultSet.getInt(1);
//				String nomQuestion = resultSet.getString(2);
//
//				questions.add(new Question(idQuestion, nomQuestion, getAnswersFromQuestion(connection, idQuestion)));
//			}
//		}
//		return questions;
//	}

	public ArrayList<Question> getQuestions(int nbQuestion) {

		ArrayList<Question> questions = new ArrayList<Question>();

		String query = "call get_game(?)";

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				CallableStatement statement = connection.prepareCall(query);) {

			statement.setInt(1, nbQuestion);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();

			while (resultSet.next()) {
				ArrayList<Answer> answers = new ArrayList<Answer>();

				int idQuestion = resultSet.getInt("id_question");
				String nomQuestion = resultSet.getString("desc_question");

				for (int i = 1; i < 5; i++) {
					String codeAnswer = Integer.toString(i);
					String descAnswer = resultSet.getString("desc_answer" + i);
					Boolean resAnswer = resultSet.getBoolean("is_correct" + i);
					answers.add(new Answer(codeAnswer, descAnswer, resAnswer));
				}

				Collections.shuffle(answers);

				questions.add(new Question(idQuestion, nomQuestion, answers));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}

	public Game createMultiPlayerGame(Game game) {
		String query = "INSERT INTO h5ws00fg4ypyuohr.GAME VALUES (DEFAULT, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, game.getName());
			ps.setInt(2, game.getIdLeader());
			ps.setInt(3, 1);
			ps.setString(4, game.getTime());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

			game.setIdGame(generatedKey);
			return game;

		} catch (SQLException e) {
			e.printStackTrace();
			return game;
		}
	}

	public void finishedSoloPlayerGame(Game game) {
		String queryGame = "UPDATE h5ws00fg4ypyuohr.GAME SET progress_game = 3 WHERE (id_game = ?)";

		String queryPlayer = "INSERT INTO h5ws00fg4ypyuohr.GAME_PLAYER VALUES (?, ?, ?)";

		String queryQuestion = "INSERT INTO h5ws00fg4ypyuohr.GAME_QUESTION VALUES (?, ?)";

		try (Connection connection = DriverManager.getConnection(urlCnx, loginCnx, passwordCnx);
				PreparedStatement psPlayer = connection.prepareStatement(queryPlayer);
				PreparedStatement psGame = connection.prepareStatement(queryGame);
				PreparedStatement psQuestion = connection.prepareStatement(queryQuestion);) {

			int idGame = game.getIdGame();

			psGame.setInt(1, idGame);

			psGame.execute();

			for (Player player : game.getPlayerList()) {

				psPlayer.setInt(1, idGame);
				psPlayer.setInt(2, player.getMyId());
				psPlayer.setInt(3, player.getMyScore());

				psPlayer.execute();
			}
			
			for (Question question : game.getGroupQuestions()) {

				psQuestion.setInt(1, idGame);
				psQuestion.setInt(2, question.getId());

				psQuestion.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
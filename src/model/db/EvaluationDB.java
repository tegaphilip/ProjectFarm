package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Evaluation;
import model.Project;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

public class EvaluationDB extends BaseDB{

	public static List<Evaluation> getProjectsEvaluations(Project project) throws DatabaseAccessError, InvalidDataException {
		List<Evaluation> evaluation = new LinkedList<Evaluation>();
		
		try {
			ResultSet result = QueryHelper.getProjectEvaluations(project.getId());
			
			if (result != null) {
				while (result.next()) {
					Evaluation e = new Evaluation(
							UserDB.getEvaluatorById(result.getInt("evaluator_id")),
							result.getInt("attractiveness"), 
							result.getInt("risk_level")
							);
					
					evaluation.add(e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return evaluation;

	}
}

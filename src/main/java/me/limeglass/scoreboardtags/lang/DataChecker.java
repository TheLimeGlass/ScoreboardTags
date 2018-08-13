package me.limeglass.scoreboardtags.lang;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import me.limeglass.scoreboardtags.ScoreboardTags;

public interface DataChecker {

	public default boolean isNull(Event event, Expression<?> expression) {
		if (expression == null) return true;
		boolean are = false;
		if (expression.isSingle()) {
			if (expression.getSingle(event) == null) are = true;
		} else {
			Object[] array = expression.getAll(event);
			if (array == null || array.length <= 0) are = true;
		}
		if (are) {
			ScoreboardTags.debugMessage("An expression was null: " + expression.toString(event, true));
			return true;
		}
		return are;
	}
	
	public default <T> boolean areNull(Event event, ExpressionData expressions) {
		if (expressions.getExpressions() == null) return true;
		for (Expression<?> expression : expressions.getExpressions()) {
			if (isNull(event, expression)) return true;
		}
		return false;
	}
	
	public default <T> boolean isNull(Event event, ExpressionData expressions, @SuppressWarnings("unchecked") Class<T>... types) {
		Map<Expression<?>, T[]> map = expressions.getAllMapOf(event, types);
		if (map == null || map.isEmpty()) return true;
		for (Entry<Expression<?>, T[]> entry : map.entrySet()) {
			if (isNull(event, entry.getKey())) return true;
		}
		return false;
	}
	
	public default boolean isNull(Event event, ExpressionData expressions, int index) {
		return isNull(event, expressions.get(index));
	}

}
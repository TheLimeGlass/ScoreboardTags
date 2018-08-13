package me.limeglass.scoreboardtags.elements.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.scoreboardtags.lang.ScoreboardTagsCondition;
import me.limeglass.scoreboardtags.utils.annotations.Patterns;

@Name("ScoreboardTags - has tag")
@Description("Check if the entity has any scoreboard tags.")
@Patterns({"%entity% (1¦has|2¦does(n't| not) have) ([a[ny]]|%-strings%) scoreboard tag[s]", "%entity% (1¦has|2¦does(n't| not) have) scoreboard tag[s] %strings%"})
public class CondHasScoreboardTag extends ScoreboardTagsCondition {

	public boolean check(Event event) {
		if (isNull(event, 0)) return !isNegated();
		Entity entity = expressions.getSingle(event, Entity.class);
		if (areNull(event)) return entity.getScoreboardTags().isEmpty() ? !isNegated() : isNegated();
		for (String tag : expressions.getAll(event, String.class)) {
			if (!entity.getScoreboardTags().contains(tag) && isNegated()) return !isNegated();
		}
		return isNegated();
	}

}
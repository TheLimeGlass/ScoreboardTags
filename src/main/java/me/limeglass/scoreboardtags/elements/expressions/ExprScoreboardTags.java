package me.limeglass.scoreboardtags.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.scoreboardtags.lang.ScoreboardTagsPropertyExpression;
import me.limeglass.scoreboardtags.utils.annotations.AllChangers;
import me.limeglass.scoreboardtags.utils.annotations.Properties;
import me.limeglass.scoreboardtags.utils.annotations.PropertiesAddition;

@Name("ScoreboardTags - tag")
@Description("Returns or changes the scoreboard tags of the entities.")
@Properties({"entities", "scoreboard tag[s]", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("[(entity|entities)]")
@AllChangers
public class ExprScoreboardTags extends ScoreboardTagsPropertyExpression<Entity, String> {
	
	@Override
	protected String[] get(Event event, Entity[] entities) {
		if (isNull(event)) return null;
		for (Entity entity : entities) collection.addAll(entity.getScoreboardTags());
		return collection.toArray(new String[collection.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		for (Entity entity : (Entity[])expressions.get(0).getAll(event)) {
			switch(mode) {
				case ADD:
					for (String tag : (String[]) delta) entity.addScoreboardTag(tag);
					break;
				case RESET:
				case DELETE:
					for (String tag : entity.getScoreboardTags()) entity.removeScoreboardTag(tag);
					break;
				case REMOVE_ALL:
				case REMOVE:
					for (String tag : (String[]) delta) entity.removeScoreboardTag(tag);
					break;
				case SET:
					for (String tag : entity.getScoreboardTags()) entity.removeScoreboardTag(tag);
					for (String tag : (String[]) delta) entity.addScoreboardTag(tag);
					break;
			}
		}
	}

}
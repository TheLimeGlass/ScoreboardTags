# ScoreboardTags
A Skript addon dedicated to scoreboard tags.

Example:
```
on join:
	if player doesn't have any scoreboard tags:
		broadcast "test1"
		add "testing" to scoreboard tags of player
		if player has any scoreboard tags:
			broadcast "test2"
			if player has scoreboard tag "testing":
				broadcast "test3"
```

Syntax:
```
Syntax:
  Conditions:
    CondHasScoreboardTag:
      enabled: true
      description: Check if the entity has any scoreboard tags.
      syntax:
      - '%entity% (1¦has|2¦does(n''t| not) have) ([a[ny]]|%-strings%) scoreboard tag[s]'
      - '%entity% (1¦has|2¦does(n''t| not) have) scoreboard tag[s] %strings%'
  PropertyExpressions:
    ExprScoreboardTags:
      enabled: true
      changers: All changers
      description: Returns or changes the scoreboard tags of the entities.
      syntax:
      - '[(all [[of] the]|the)] scoreboard tag[s] (of|from|in) [(entity|entities)] %entities%'
```
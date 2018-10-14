package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.event.Event;


public class ExprRegionPoints extends SimpleExpression<Vector> {

    static {
        Skript.registerExpression(ExprRegionPoints.class, Vector.class, ExpressionType.PROPERTY, "[the] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %weregions%" + "%weregions%'[s] (1¦min|2¦max)[imum] (coord[inate]|point)[s]");
    }

    enum Point {
        MIN, MAX
    }

    private Expression<Region> player;
    private Point point;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        point = Point.values()[parseResult.mark];

        player = (Expression<Region>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the min and max points of the region of " + player.toString(e, debug) ;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected Vector[] get(Event e) {
        Vector r = null;
        switch (point) {
            case MAX:
                r = FaweAPI.wrapPlayer(player.getSingle(e)).getSelection().getMinimumPoint();
                break;
            case MIN:
                r = FaweAPI.wrapPlayer(player.getSingle(e)).getSelection().getMaximumPoint();
                break;
        } return new Vector[] { r };
    }

}
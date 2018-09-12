package me.efnilite.skematic.syntaxes;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;

public class ExprSchematicArea extends SimpleExpression<Integer> {

    private Expression<String> schem;
    private int marker;

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        marker = parser.mark;
        schem = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Example expression with expression player: " + schem.toString(event, debug);
    }

    @Override
    @Nullable
    protected Integer[] get(Event event) {

        Vector size = null;
        File file = new File(schem.getSingle(event));
        try {
            FaweAPI.load(file).getClipboard().getDimensions();
        } catch (IOException exception) {
            return null;
        }
        Number result = null;
        if (marker == 1) {
            result = size.getY();
        } else if (marker == 2) {
            result = size.getX();
        } else if (marker == 3) {
            result = size.getZ();
        } else if (marker == 4) {
            result = (size.getZ() * size.getX());
        }
        return new Integer[] { result.intValue() };

    }
}


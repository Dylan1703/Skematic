package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Clear contents")
@Description("Clear the contents of a container at a location.")
@Examples("clear the contents of the container at 3, 73, 12 in \"world\"")
public class EffClearContainer extends SkematicEffect {

    static {
        Skript.registerEffect(EffClearContainer.class, "(clear|delete) [the] [(fawe|skematic)] content[s] of [the] [container] %direction% %location%");
    }

    @Override
    protected void execute(Event e) {
        Location location = Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]).getSingle(e);

        if (location == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.clearContainerBlockContents(FaweUtils.toVector(location));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "clear contents of " + expressions[1].toString(e, debug);
    }
}

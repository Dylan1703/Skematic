package com.efnilite.skematic.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.util.TreeGenerator;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Shape - Line")
@Description("Create a line between 2 locations")
@Patterns({"fill faces of %cuboidregions% with %itemtypes%",
            "fill %cuboidregions%'[s] faces with %itemtypes%"})
public class EffCuboidFaces extends SkematicEffect {

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[1].getAll(e);

        if (blocks == null || cuboid == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.makeCuboidFaces(cuboid, FaweTools.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill faces of " + expressions[0].toString(e, debug) + " with pattern " + expressions[1].toString(e, debug);
    }
}

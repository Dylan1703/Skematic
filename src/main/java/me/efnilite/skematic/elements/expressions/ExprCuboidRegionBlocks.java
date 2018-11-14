package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprCuboidRegionBlocks extends SimpleExpression<Material> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocks.class, Material.class, ExpressionType.PROPERTY,
                "[all] [of] [the] [skematic] blocks in %cuboidregions%",
                "[all] [of] %cuboidregions%'s [skematic] blocks");
    }

    private Expression<CuboidRegion> region;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        region = (Expression<CuboidRegion>) exprs[0];

        return true;
    }

    @Override
    protected Material[] get(Event e) {
        CuboidRegion r = region.getSingle(e);

        if (r == null) {
            return null;
        }

        Vector pos1 = r.getPos1();
        Vector pos2 = r.getPos2();

        World w = Bukkit.getServer().getWorld(r.getWorld().getName());

        ArrayList<Material> blocks = new ArrayList<>();
        for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
            for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
                for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                    Material block = w.getBlockAt(x, y, z).getType();
                    blocks.add(block);
                }
            }
        }
        return blocks.toArray(new Material[0]);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "all blocks of " + region.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Material> getReturnType() {
        return Material.class;
    }
}

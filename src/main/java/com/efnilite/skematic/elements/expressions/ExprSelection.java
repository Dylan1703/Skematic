package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

@Name("Selection")
@Description("Gets the selection of a player (region)")
@Examples("set {_size} to selection of player")
@Patterns({"selection", "players"})
@Return(Region.class)
@PropertyExpression
public class ExprSelection extends SkematicPropertyExpression<Player, Region> {

    static {
        register(ExprSelection.class, Region.class, "selection[s]", "players");
    }

    @Override
    public Region convert(final Player p) {
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        Region selection;
        try {
            selection = session.getSelection(session.getSelectionWorld());
        } catch (IncompleteRegionException e) {
            return null;
        }
        return selection;
    }

}
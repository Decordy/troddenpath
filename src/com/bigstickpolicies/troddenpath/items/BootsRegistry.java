package com.bigstickpolicies.troddenpath.items;

import com.bigstickpolicies.troddenpath.TroddenPath;
import com.bigstickpolicies.troddenpath.tread.BootsEffectHook;
import com.bigstickpolicies.troddenpath.tread.PathTreader;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BootsRegistry {
    private Map<String, BootsEffectHook> nameMap=new HashMap();
    public BootsRegistry() {
        nameMap.put(null,(block,data,effector) -> {
            if(!TroddenPath.globalConfigs.isDestroyable(block.getLocation().add(0,1,0).getBlock().getType())) return;
            for(var part:data) {
                if(Math.random()>part.chance()) continue;
                block.setType(part.to());
            }
            return;
        });
    }
    public static String loreHash(ItemStack item) {
        if(item==null) return "";
        if(item.getType().isAir()) return "";
        var meta=item.getItemMeta();
        var total=item.getType().toString();
        if(meta.getLore()!=null) {
            for(var s:meta.getLore()) {
                total+=s;
            }
        }

        return total;
    }
    public void register(ItemStack boots,BootsEffectHook hook) {
        nameMap.put(loreHash(boots),hook);
    }
    public @NotNull BootsEffectHook get(ItemStack boots) {
        var effect=nameMap.get(loreHash(boots));
        if(effect==null) return nameMap.get(null);
        return effect;
    }
    public @NotNull BootsEffectHook getDefault() {
        return get(null);
    }

}
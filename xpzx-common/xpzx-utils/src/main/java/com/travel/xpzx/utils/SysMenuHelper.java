package com.travel.xpzx.utils;

import com.travel.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class SysMenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> menuNodes) {
        List<SysMenu> trees = new ArrayList<>();
        for (SysMenu node : menuNodes) {
            if (node.getParentId() == 0) {
                trees.add(findChildren(node, menuNodes));
            }
        }
        return trees;
    }

    private static SysMenu findChildren(SysMenu node, List<SysMenu> menuNodes) {

        for (SysMenu child : menuNodes) {
            if (child.getParentId() == node.getId()) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildren(child, menuNodes));
            }
        }
        return node;

    }
}
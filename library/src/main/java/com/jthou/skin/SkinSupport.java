package com.jthou.skin;

import android.content.Context;
import android.util.AttributeSet;

import com.jthou.skin.attr.SkinAttr;
import com.jthou.skin.attr.SkinAttrType;

import java.util.LinkedList;
import java.util.List;

public class SkinSupport {

    public static List<SkinAttr> getAttrs(Context context, AttributeSet attrs) {
        SkinAttr attr;
        SkinAttrType attrType;
        List<SkinAttr> skinAttrs = new LinkedList<>();
        for (int i = 0, N = attrs.getAttributeCount(); i < N; i++) {
            String attributeValue = attrs.getAttributeValue(i);
            if (!attributeValue.startsWith("@")) continue;
            int id = -1;
            try {
                id = Integer.parseInt(attributeValue.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (id == -1) continue;
            String resName = context.getResources().getResourceEntryName(id);
            if (!resName.startsWith(Constant.PREFIX)) continue;
            String attrName = attrs.getAttributeName(i);
            attrType = SkinAttrType.Companion.attrNameOf(attrName);
            if (attrType == null) continue;
            attr = new SkinAttr(resName, attrType);
            skinAttrs.add(attr);
        }
        return skinAttrs;
    }

}

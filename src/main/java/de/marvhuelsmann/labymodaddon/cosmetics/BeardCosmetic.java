package de.marvhuelsmann.labymodaddon.cosmetics;

public class BeardCosmetic //extends CosmeticRenderer


{

   /* public static final int ID = 7;
    private static final HashMap<String, ResourceLocation> flags = new HashMap();
    private ModelRenderer witchHat;

    public BeardCosmetic() {}

    public void addModels(ModelCosmetics modelCosmetics, float modelSize) {
        this.witchHat = (new ModelRenderer(modelCosmetics)).setTextureSize(40, 34);
        this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
        this.witchHat.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
        ModelRenderer modelrenderer = (new ModelRenderer(modelCosmetics)).setTextureSize(40, 34);
        modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer.setTextureOffset(0, 12).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
        modelrenderer.rotateAngleX = -0.05235988F;
        modelrenderer.rotateAngleZ = 0.02617994F;
        this.witchHat.addChild(modelrenderer);
        ModelRenderer modelrenderer1 = (new ModelRenderer(modelCosmetics)).setTextureSize(40, 34);
        modelrenderer1.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer1.setTextureOffset(0, 23).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        modelrenderer1.rotateAngleX = -0.10471976F;
        modelrenderer1.rotateAngleZ = 0.05235988F;
        modelrenderer.addChild(modelrenderer1);
        ModelRenderer modelrenderer2 = (new ModelRenderer(modelCosmetics)).setTextureSize(40, 34);
        modelrenderer2.setRotationPoint(1.75F, -2.0F, 2.0F);
        modelrenderer2.setTextureOffset(0, 31).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
        modelrenderer2.rotateAngleX = -0.20943952F;
        modelrenderer2.rotateAngleZ = 0.10471976F;
        modelrenderer1.addChild(modelrenderer2);
        this.witchHat.isHidden = true;
    }

    public void setInvisible(boolean invisible) {
        this.witchHat.showModel = invisible;
    }

    @Override
    public void render(ModelCosmetics modelCosmetics, Entity entity, Object o, float v, float v1, float v2, float v3, float v4, float v5, boolean b) {
        ResourceLocation bindedTexture = ModTextures.COSMETIC_WITCH;
        if (cosmeticData.getFlagTexture() != null) {
            if (!flags.containsKey(cosmeticData.getFlagTexture())) {
                flags.put(cosmeticData.getFlagTexture(), new ResourceLocation("labymod/textures/cosmetics/flags/" + cosmeticData.getFlagTexture() + ".png"));
            }

            ResourceLocation loadedTexture = (ResourceLocation)flags.get(cosmeticData.getFlagTexture());
            if (loadedTexture != null) {
                bindedTexture = loadedTexture;
            }
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(bindedTexture);
        GlStateManager.pushMatrix();
        float scaleDown = 0.995F;
        GlStateManager.scale(scaleDown, scaleDown, scaleDown);
        if (entityIn.isSneaking()) {
            GlStateManager.translate(0.0D, 0.1D, 0.0D);
        }

        GlStateManager.rotate(firstRotationX, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(secondRotationX, 1.0F, 0.0F, 0.0F);
        this.witchHat.isHidden = false;
        this.witchHat.render(scale);
        this.witchHat.isHidden = true;
        GlStateManager.popMatrix();
    }


    public int getCosmeticId() {
        return 7;
    }

    public String getCosmeticName() {
        return "Hat";
    }

    public boolean isOfflineAvailable() {
        return false;
    }

    public float getNameTagHeight() {
        return 0.5F;
    }

    public static class CosmeticWitchHatData extends CosmeticData {
        private String flagTexture;

        public CosmeticWitchHatData() {
        }

        public boolean isEnabled() {
            return true;
        }

        public void loadData(String[] data) throws Exception {
            this.flagTexture = data[0] != null && !data[0].isEmpty() ? data[0].toLowerCase() : null;
        }

        public String getFlagTexture() {
            return this.flagTexture;
        }
    }


    */
}
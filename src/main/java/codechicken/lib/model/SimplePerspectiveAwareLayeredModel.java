package codechicken.lib.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by covers1624 on 19/11/2016.
 */
public class SimplePerspectiveAwareLayeredModel implements IPerspectiveAwareModel {

    private final ImmutableMap<BlockRenderLayer, Map<EnumFacing, List<BakedQuad>>> layerFaceQuadMap;
    private final ImmutableMap<TransformType, TRSRTransformation> cameraTransforms;
    private final BakedModelProperties properties;

    public SimplePerspectiveAwareLayeredModel(Map<BlockRenderLayer, Map<EnumFacing, List<BakedQuad>>> layerFaceQuadMap, Map<TransformType, TRSRTransformation> cameraTransforms, BakedModelProperties properties) {

        this.layerFaceQuadMap = ImmutableMap.copyOf(layerFaceQuadMap);
        this.cameraTransforms = ImmutableMap.copyOf(cameraTransforms);
        this.properties = properties.copy();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side == null){
            return new ArrayList<BakedQuad>();
        }
        BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
        if (layerFaceQuadMap.containsKey(layer)) {
            Map<EnumFacing, List<BakedQuad>> faceQuadMap = layerFaceQuadMap.get(layer);
            if (faceQuadMap.containsKey(side)) {
                return faceQuadMap.get(side);
            }
        }
        return new ArrayList<BakedQuad>();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return properties.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return properties.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return properties.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return properties.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        return MapWrapper.handlePerspective(this, cameraTransforms, cameraTransformType);
    }
}

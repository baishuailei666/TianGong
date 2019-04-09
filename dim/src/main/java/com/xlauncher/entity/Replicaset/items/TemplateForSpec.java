package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 17:10
 */
public class TemplateForSpec {
    private MetadataForTemplate metadata;
    private SpecForTemplate spec;

    public MetadataForTemplate getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataForTemplate metadata) {
        this.metadata = metadata;
    }

    public SpecForTemplate getSpec() {
        return spec;
    }

    public void setSpec(SpecForTemplate spec) {
        this.spec = spec;
    }
}

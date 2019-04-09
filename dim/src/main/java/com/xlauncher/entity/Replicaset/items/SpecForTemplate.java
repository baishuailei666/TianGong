package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 17:13
 */
public class SpecForTemplate {
    private MetadataForTemplateIn metadata;
    private SpecForTemplateIn spec;

    public MetadataForTemplateIn getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataForTemplateIn metadata) {
        this.metadata = metadata;
    }

    public SpecForTemplateIn getSpec() {
        return spec;
    }

    public void setSpec(SpecForTemplateIn spec) {
        this.spec = spec;
    }
}

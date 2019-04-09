package com.xlauncher.entity.deployment;

import com.xlauncher.entity.deployment.spec.MetadataIn;
import com.xlauncher.entity.deployment.spec.SpecIn;
import org.springframework.stereotype.Component;

/**
 * Kubernetes Template实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:00
 */
@Component
public class Template {
    private com.xlauncher.entity.deployment.spec.MetadataIn metadata;
    private com.xlauncher.entity.deployment.spec.SpecIn spec;

    public MetadataIn getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataIn metadata) {
        this.metadata = metadata;
    }

    public SpecIn getSpec() {
        return spec;
    }

    public void setSpec(SpecIn spec) {
        this.spec = spec;
    }
}

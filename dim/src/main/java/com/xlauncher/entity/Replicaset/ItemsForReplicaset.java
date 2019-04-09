package com.xlauncher.entity.Replicaset;

import com.xlauncher.entity.Replicaset.items.MetadataForItem;
import com.xlauncher.entity.Replicaset.items.SpecForItem;
import com.xlauncher.entity.Replicaset.items.StatusForItem;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:45
 */
public class ItemsForReplicaset {
    private MetadataForItem metadata;
    private SpecForItem spec;
    private StatusForItem status;

    public MetadataForItem getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataForItem metadata) {
        this.metadata = metadata;
    }

    public SpecForItem getSpec() {
        return spec;
    }

    public void setSpec(SpecForItem spec) {
        this.spec = spec;
    }

    public StatusForItem getStatus() {
        return status;
    }

    public void setStatus(StatusForItem status) {
        this.status = status;
    }
}

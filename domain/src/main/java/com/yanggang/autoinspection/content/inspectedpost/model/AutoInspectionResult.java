package com.yanggang.autoinspection.content.inspectedpost.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AutoInspectionResult {

    private String status; // 결과 (GOOD or BAD)
    private String[] tags;

    @Builder
    public AutoInspectionResult(String status, String[] tags) {
        this.status = status;
        this.tags = tags;
    }

    public boolean isBadStatus() {
        return this.status.equals("BAD");
    }
}

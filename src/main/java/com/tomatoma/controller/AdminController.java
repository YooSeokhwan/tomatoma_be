package com.tomatoma.controller;

import com.tomatoma.dto.ResponseDTO;
import com.tomatoma.scheduler.TrendUpdaterScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final TrendUpdaterScheduler trendUpdaterScheduler;
    private final TaskExecutor taskExecutor;

    public AdminController(TrendUpdaterScheduler trendUpdaterScheduler, TaskExecutor taskExecutor) {
        this.trendUpdaterScheduler = trendUpdaterScheduler;
        this.taskExecutor = taskExecutor;
    }

    /**
     * POST /api/admin/trigger-crawl
     * 트렌드 크롤링 즉시 실행 (테스트용)
     */
    @PostMapping("/trigger-crawl")
    public ResponseEntity<ResponseDTO<String>> triggerCrawl() {
        log.info("Manual crawl trigger requested");
        taskExecutor.execute(trendUpdaterScheduler::updateTrendingFoods);
        return ResponseEntity.ok(ResponseDTO.success("크롤링이 백그라운드에서 시작되었습니다."));
    }
}

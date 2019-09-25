package com.griddynamics.gridquiz.core.controllers;

public class OldAdminController {

    //    @Autowired
    //    private QuizRepository quizRepository;
    //
    //    @Autowired
    //    private QuizService quizResultService;
    //
    //    @Autowired
    //    private ReportService reportService;

    //    @PostMapping(value = "/users")
    //    @ResponseBody
    //    public List<UserDashboardResultModel> getUsers(
    //            @RequestHeader(value = "X-User-Token") String userToken) {
    //        //        securityValidationService.isAdmin(userToken);
    //        //
    //        //        return quizResultService.getUsers();
    //        return new ArrayList<>();
    //    }
    //
    //    @PostMapping(value = "/users/remove")
    //    @ResponseBody
    //    public List<UserDashboardResultModel> removeUsers(
    //            @RequestHeader(value = "X-User-Token") String userToken,
    //            @RequestBody List<Long> userIds) {
    //
    //        //        // remove user results story
    //        //        seq(userIds).forEach(userId -> resultRepository.removeByUser(userRepository.findOne(userId)));
    //        //        // delete users
    //        //        seq(userIds).forEach(userId -> userRepository.delete(userId));
    //
    //        //return quizResultService.getUsers();
    //        return null;
    //    }
    //
    //    @PostMapping(value = "/non/approved")
    //    @ResponseBody
    //    public List<NonApprovedModel> preparingOnApprove(
    //            @RequestHeader(value = "X-User-Token") String userToken) {
    //        //return quizResultService.nonApproved();
    //        return null;
    //    }
    //
    //    @PostMapping(value = "/dashboard/approve")
    //    @ResponseBody
    //    public List<NonApprovedModel> approve(@RequestHeader(value = "X-User-Token") String userToken,
    //                                          @RequestBody List<Long> resultsIds) {
    //        //
    //        //        seq(resultsIds).forEach(id -> {
    //        //                    UserResult r = resultRepository.findOne(id);
    //        //                    r.setApproved(true);
    //        //                    resultRepository.save(r);
    //        //                }
    //        //        );
    //
    //        //return quizResultService.nonApproved();
    //        return null;
    //    }
    //
    //    @GetMapping(value = "/download/report")
    //    public void downloadReport(HttpServletResponse response) throws IOException {
    //        FileDownload.newFileDownload()
    //                .withData(reportService.generateReport())
    //                .withFileName(String.format("GridQuiz Report %s.xlsx", LocalDate.now()))
    //                .applyToResponse(response)
    //                .withXlsxContentType()
    //                .send();
    //    }
}

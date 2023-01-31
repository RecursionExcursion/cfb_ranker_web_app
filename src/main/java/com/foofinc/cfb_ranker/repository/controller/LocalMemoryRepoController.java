package com.foofinc.cfb_ranker.repository.controller;


import com.foofinc.cfb_ranker.repository.api.CfbApiAccess;
import com.foofinc.cfb_ranker.repository.api.dto.CompleteGameDto;
import com.foofinc.cfb_ranker.repository.model.ModelGenerator;
import com.foofinc.cfb_ranker.repository.model.School;
import com.foofinc.cfb_ranker.repository.model.SchoolList;
import com.foofinc.cfb_ranker.repository.model.Schools;
import com.foofinc.cfb_ranker.service.entity.StatisticizedTeam_2;
import com.foofinc.cfb_ranker.service.entity.WeeklyRanker;

import java.util.List;
import java.util.Map;

public class LocalMemoryRepoController {

    private static LocalMemoryRepoController instance;

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //List<Schools> wrapped in class, this object is the serializable form of the data
    private final SchoolList schoolList = SchoolList.INSTANCE;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<Map<Long, CompleteGameDto>> weeks;

    private LocalMemoryRepoController() {

    }

    /*
    Checks local memory for file and loads the serialized data to minimize calls to the CFB API
    If the file does not exist then this method will make the api calls and serialize the data into memory.
    //TODO Implement ability to update based on date.
     */

    public static LocalMemoryRepoController getInstance() {
        if (instance == null) instance = new LocalMemoryRepoController();
        return instance;
    }

    public List<List<StatisticizedTeam_2>> retrieveData() {
        LocalMemoryController<Schools> listLocalMemoryController = new LocalMemoryController<>();
        if (listLocalMemoryController.fileExists()) {
            schoolList.loadSchools(listLocalMemoryController.load());
        } else {
            listLocalMemoryController.save((getSchoolsFromAPI()));
        }
        return new WeeklyRanker().getRankings();
    }

    private Schools getSchoolsFromAPI() {
        cfbApi = new CfbApiAccess();
        weeks = cfbApi.getWeeks();
        schoolList.loadSchools(mapSchoolsFromAPIToWrapperList());
        parseGames();
        return schoolList.getSchoolsAsSchools();
    }

    private Schools mapSchoolsFromAPIToWrapperList() {

        List<School> schoolList1 = cfbApi.getSchools()
                                         .stream()
                                         .map(ModelGenerator::generateSchool)
                                         .toList();

        return new Schools(schoolList1);
    }


    /*
    Generates games and adds them to the School model objects
     */

    private void parseGames() {
        for (Map<Long, CompleteGameDto> week : weeks) {
            for (Long id : week.keySet()) {
                ModelGenerator.generateGame(week.get(id));
            }
        }
    }
}

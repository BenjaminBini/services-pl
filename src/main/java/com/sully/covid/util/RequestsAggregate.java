package com.sully.covid.util;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.PublicFormRequest;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class RequestsAggregate {
    private Map<String, Long> requestsStatus = new HashMap<>();
    private Map<String, List<String>> requestsComments = new HashMap<>();

    public static RequestsAggregate fromAire(Aire aire) {
        RequestsAggregate ag = new RequestsAggregate();
        Collection<PublicFormRequest> requests = aire.getPublicFormRequests();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        ag.requestsStatus.put("statutOuvertOui", requests.stream().filter(r -> r.isStatutOuvert()).count());
        ag.requestsComments.put("statutOuvertOui", requests.stream().filter(r -> r.isStatutOuvert()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));


        ag.requestsStatus.put("statutOuvertNon", requests.stream().filter(r -> !r.isStatutOuvert()).count());
        ag.requestsComments.put("statutOuvertNon", requests.stream().filter(r -> !r.isStatutOuvert()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqPlacesPlOui", requests.stream().filter(r -> r.isEqPlacesPl()).count());
        ag.requestsComments.put("eqPlacesPlOui", requests.stream().filter(r -> r.isEqPlacesPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqPlacesPlNon", requests.stream().filter(r -> !r.isEqPlacesPl()).count());
        ag.requestsComments.put("eqPlacesPlNon", requests.stream().filter(r -> !r.isEqPlacesPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqSanitairesOui", requests.stream().filter(r -> r.isEqSanitaires()).count());
        ag.requestsComments.put("eqSanitairesOui", requests.stream().filter(r -> r.isEqSanitaires()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqSanitairesNon", requests.stream().filter(r -> !r.isEqSanitaires()).count());
        ag.requestsComments.put("eqSanitairesNon", requests.stream().filter(r -> !r.isEqSanitaires()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servSanitairesOui", requests.stream().filter(r -> r.isServSanitaires()).count());
        ag.requestsComments.put("servSanitairesOui", requests.stream().filter(r -> r.isServSanitaires()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servSanitairesNon", requests.stream().filter(r -> !r.isServSanitaires()).count());
        ag.requestsComments.put("servSanitairesNon", requests.stream().filter(r -> !r.isServSanitaires()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqDouchesOui", requests.stream().filter(r -> r.isEqDouches()).count());
        ag.requestsComments.put("eqDouchesOui", requests.stream().filter(r -> r.isEqDouches()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqDouchesNon", requests.stream().filter(r -> !r.isEqDouches()).count());
        ag.requestsComments.put("eqDouchesNon", requests.stream().filter(r -> !r.isEqDouches()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servDouchesOui", requests.stream().filter(r -> r.isServDouches()).count());
        ag.requestsComments.put("servDouchesOui", requests.stream().filter(r -> r.isServDouches()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servDouchesNon", requests.stream().filter(r -> !r.isServDouches()).count());
        ag.requestsComments.put("servDouchesNon", requests.stream().filter(r -> !r.isServDouches()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqRestauOui", requests.stream().filter(r -> r.isEqRestau()).count());
        ag.requestsComments.put("eqRestauOui", requests.stream().filter(r -> r.isEqRestau()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqRestauNon", requests.stream().filter(r -> !r.isEqRestau()).count());
        ag.requestsComments.put("eqRestauNon", requests.stream().filter(r -> !r.isEqRestau()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servRestauOui", requests.stream().filter(r -> r.isServRestau()).count());
        ag.requestsComments.put("servRestauOui", requests.stream().filter(r -> r.isServRestau()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servRestauNon", requests.stream().filter(r -> !r.isServRestau()).count());
        ag.requestsComments.put("servRestauNon", requests.stream().filter(r -> !r.isServRestau()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqCarbuPlOui", requests.stream().filter(r -> r.isEqCarbuPl()).count());
        ag.requestsComments.put("eqCarbuPlOui", requests.stream().filter(r -> r.isEqCarbuPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("eqCarbuPlNon", requests.stream().filter(r -> !r.isEqCarbuPl()).count());
        ag.requestsComments.put("eqCarbuPlNon", requests.stream().filter(r -> !r.isEqCarbuPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servCarbuPlOui", requests.stream().filter(r -> r.isServCarbuPl()).count());
        ag.requestsComments.put("servCarbuPlOui", requests.stream().filter(r -> r.isServCarbuPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));

        ag.requestsStatus.put("servCarbuPlNon", requests.stream().filter(r -> !r.isServCarbuPl()).count());
        ag.requestsComments.put("servCarbuPlNon", requests.stream().filter(r -> !r.isServCarbuPl()).map(PublicFormRequest::getCommentForDisplay).collect(Collectors.toList()));
        return ag;
    }
}


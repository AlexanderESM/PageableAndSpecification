package net.orekhov.pageableandspecification.model;

public record RequestDto(
        Integer page,
        Integer size,
        String sortField,
        String sortDirection,
        String numberPassport,
        String name,
        String surname,
        String age,
        String sex,

        String startAge,
        String finishAge
) {

    public RequestDto {
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (sortField == null) sortField = "numberPassport";
        if (sortDirection == null) sortDirection = "asc";
    }
}

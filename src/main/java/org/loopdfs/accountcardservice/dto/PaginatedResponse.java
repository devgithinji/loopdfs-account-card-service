package org.loopdfs.accountcardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private boolean lastPage;
    private int requestedElementsNumber;
    private int pageNumber;
    private boolean firstPage;
    private int numberOfElements;

}

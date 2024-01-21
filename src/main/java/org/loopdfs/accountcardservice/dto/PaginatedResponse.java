package org.loopdfs.accountcardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(name = "PaginatedResponse", description = "Wrapper for paginated responses")
public class PaginatedResponse<T> {
    @Schema(
            description = "List of content in the current page",
            example = "[{id: 1, name: 'Item 1'}, {id: 2, name: 'Item 2'}]"
    )
    private List<T> content;

    @Schema(
            description = "Total number of pages",
            example = "3"
    )
    private int totalPages;

    @Schema(
            description = "Total number of elements across all pages",
            example = "25"
    )
    private long totalElements;

    @Schema(
            description = "Indicates whether this is the last page",
            example = "false"
    )
    private boolean lastPage;

    @Schema(
            description = "Number of elements requested in the current page",
            example = "10"
    )
    private int requestedElementsNumber;

    @Schema(
            description = "Current page number",
            example = "1"
    )
    private int pageNumber;

    @Schema(
            description = "Indicates whether this is the first page",
            example = "true"
    )
    private boolean firstPage;

    @Schema(
            description = "Number of elements in the current page",
            example = "10"
    )
    private int numberOfElements;

}

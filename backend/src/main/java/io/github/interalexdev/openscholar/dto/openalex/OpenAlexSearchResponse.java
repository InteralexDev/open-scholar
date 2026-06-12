package io.github.interalexdev.openscholar.dto.openalex;

import java.util.List;

public record OpenAlexSearchResponse(
        List<OpenAlexWorkDto> results
) {
}

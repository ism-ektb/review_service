package ru.yandex.review_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.review_service.model.Review;
import ru.yandex.review_service.model.ReviewOutDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Microsoft)"
)
@Component
public class ReviewListMapperImpl implements ReviewListMapper {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<ReviewOutDto> modelsToDtos(List<Review> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ReviewOutDto> list = new ArrayList<ReviewOutDto>( dtos.size() );
        for ( Review review : dtos ) {
            list.add( reviewMapper.modelToDto( review ) );
        }

        return list;
    }
}

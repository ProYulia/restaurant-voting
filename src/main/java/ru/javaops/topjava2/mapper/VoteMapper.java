package ru.javaops.topjava2.mapper;

import org.mapstruct.Mapper;
import ru.javaops.topjava2.config.MapConfig;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteResponseTo;

@Mapper(config = MapConfig.class)
public interface VoteMapper {
    VoteResponseTo entityToVoteResponseTo(Vote vote);
}

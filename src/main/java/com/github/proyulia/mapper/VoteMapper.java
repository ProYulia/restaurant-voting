package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Vote;
import com.github.proyulia.to.VoteTo;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface VoteMapper {
    VoteTo entityToVoteResponseTo(Vote vote);
}

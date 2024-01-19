package com.github.proyulia.mapper;

import com.github.proyulia.config.MapConfig;
import com.github.proyulia.model.Vote;
import com.github.proyulia.to.VoteResponseTo;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface VoteMapper {
    VoteResponseTo entityToVoteResponseTo(Vote vote);
}

package org.esprit.storeyc.services.interfaces;

import org.esprit.storeyc.dto.CharityDto;

import java.util.List;

public interface ICharityService {
     CharityDto createCharity(CharityDto charityDto);
     CharityDto updateCharity(Integer id, CharityDto charityDto) ;
     boolean deleteCharity(Integer id) ;
     List<CharityDto> getAllCharities() ;
     CharityDto getCharityById(Integer id) ;
     String assignCharityToCommand(Integer idCommand, Integer IdCharity);
     String markCommandAsDonation(Integer idCommand) ;
     }

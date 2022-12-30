package pl.witoldbrzezinski.libraryapp.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class LibraryAppConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    return modelMapper;
  }

  @Bean
  public Clock getClock() {
    return Clock.systemDefaultZone();
  }
}

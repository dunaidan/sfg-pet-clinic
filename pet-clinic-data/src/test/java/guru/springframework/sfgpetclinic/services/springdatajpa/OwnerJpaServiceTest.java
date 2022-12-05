package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {
    public static final String LAST_NAME = "Smith";
    public static final long ID = 1L;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        //given
        when(ownerRepository.findByLastName(LAST_NAME)).thenReturn(returnOwner);

        //when
        Owner smith = ownerSDJpaService.findByLastName(LAST_NAME);

        //then
        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        //given
        Set<Owner> returnOwners = new HashSet<>();
        returnOwners.add(Owner.builder().id(ID).build());
        returnOwners.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwners);

        //when
        Set<Owner> owners = ownerSDJpaService.findAll();

        //then
        assertEquals(returnOwners.size(), owners.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        //given
        when(ownerRepository.findById(ID)).thenReturn(Optional.ofNullable(returnOwner));

        //when
        Owner owner = ownerSDJpaService.findById(ID);

        //then
        assertNotNull(owner);
        assertEquals(ID, owner.getId());
    }

    @Test
    void save() {
        //given
        when(ownerRepository.save(returnOwner)).thenReturn(returnOwner);

        //when
        Owner owner = ownerSDJpaService.save(returnOwner);

        //then
        assertNotNull(owner);
        assertEquals(returnOwner.getId(), owner.getId());
        verify(ownerRepository).save(any());

    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(ID);

        verify(ownerRepository).deleteById(anyLong());
    }
}
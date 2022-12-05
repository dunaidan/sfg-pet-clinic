package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    final Long ownerId = 1L;
    final String lastName = "Smith";
    OwnerMapService ownerMapService;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        //given
        //when
        Set<Owner> ownerSet = ownerMapService.findAll();

        //then
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        //given
        //when
        Owner owner = ownerMapService.findById(ownerId);

        //then
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        //given
        Long id = 2l;
        Owner owner2 = Owner.builder().id(id).build();

        //when
        Owner savedOwner = ownerMapService.save(owner2);

        //then
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        //given
        Owner owner3 = Owner.builder().build();

        //when
        Owner savedOwner = ownerMapService.save(owner3);

        //then
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        //given

        //when
        ownerMapService.delete(ownerMapService.findById(ownerId));

        //then
        assertEquals(0, ownerMapService.findAll().size());

    }

    @Test
    void deleteById() {
        //when
        ownerMapService.deleteById(ownerId);

        //then
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        //when
        Owner owner = ownerMapService.findByLastName(lastName);

        //then
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        //when
        Owner owner = ownerMapService.findByLastName("Foo");

        //then
        assertNull(owner);
    }
}
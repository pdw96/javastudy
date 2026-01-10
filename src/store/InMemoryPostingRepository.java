package store;

import domain.Posting;

import java.util.*;

public class InMemoryPostingRepository implements PostingRepository {

    private final Map<Long, Posting> data = new HashMap<>();

    @Override
    public void save(Posting posting) {
        data.put(posting.getId(), posting);
    }

    @Override
    public Optional<Posting> findById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Posting> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean deleteById(long id) {
        return data.remove(id) != null;
    }
}

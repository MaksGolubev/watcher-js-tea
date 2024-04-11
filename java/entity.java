@Entity
@Table(name = "term_entry")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TermEntryEntity {

    @Id
    @Column(name = "term_entry_id", updatable = false, nullable = false)
    private Long termEntryId;

    @Column(name = "termbase_id", length = 10, nullable = false)
    private Long termbaseId;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Column(name = "modified_on", nullable = false)
    private Long modifiedOn;

    @Column(name = "term_entry_description", columnDefinition = "longtext")
    @Convert(converter = DescriptionContentConverter.class)
    private List<DescriptionContent> termEntryDescriptions;

    @OneToMany(mappedBy = "termEntryEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TermEntity> terms = new ArrayList<>();

    public void addTerm(TermEntity termEntity) {
    terms.add(termEntity);
    termEntity.setTermEntryEntity(this);
    }

    @Override
    public boolean equals(Object o) {
    if (this == o)
        return true;
    if (o == null || getClass() != o.getClass())
        return false;
    TermEntryEntity that = (TermEntryEntity) o;
    return termEntryId.equals(that.termEntryId);
    }

    @Override
    public int hashCode() {
    return Objects.hash(termEntryId);
    }
}

@Entity
@Table(name = "term")
@NoArgsConstructor
@Getter
@Setter
public class TermEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_entry_id")
    private TermEntryEntity termEntryEntity;

    @Id
    @Column(name = "term_id", updatable = false, nullable = false)
    private Long termId;

    @Column(name = "term_revision", columnDefinition = "longtext", nullable = false)
    @Convert(converter = TermContentConverter.class)
    private TermContent termContent;

    @Override
    public boolean equals(Object o) {
    if (this == o)
        return true;
    if (o == null || getClass() != o.getClass())
        return false;
    TermEntity that = (TermEntity) o;
    return termId.equals(that.termId);
    }

    @Override
    public int hashCode() {
    return Objects.hash(termId);
    }
}
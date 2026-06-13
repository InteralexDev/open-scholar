export interface PublicationMetadata {
  openAlexId: string;
  doi: string | null;
  title: string;
  publicationYear: number | null;
  publicationDate: string | null;
  type: string | null;
  authors: string[];
  journalName: string | null;
  publisher: string | null;
  language: string | null;
  keywords: string[];
  topics: string[];
}

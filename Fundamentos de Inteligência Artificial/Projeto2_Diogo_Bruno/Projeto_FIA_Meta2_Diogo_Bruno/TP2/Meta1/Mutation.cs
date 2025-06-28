using System.Diagnostics;
using GeneticSharp.Domain.Chromosomes;
using GeneticSharp.Domain.Mutations;
using GeneticSharp.Domain.Randomizations;
using GeneticSharp.Runner.UnityApp.Car;

public class Mutation : IMutation
{
    public bool IsOrdered { get; private set; } // indicating whether the operator is ordered (if can keep the chromosome order).

    protected float mutationProbability; // Changed variable name to follow C# naming conventions

    public Mutation(float mutationProbability) // Changed parameter name to follow C# naming conventions
    {
        IsOrdered = true;
        this.mutationProbability = mutationProbability;
    }

    public void Mutate(IChromosome chromosome, float probability)
    {
        // Gera um valor aleatório entre 0 e 1
        var randomValue = RandomizationProvider.Current.GetDouble();

        // Verifica se esta a baixo da probabilidade
        if( randomValue <= mutationProbability ){
            
            // Obtém os genes do cromossoma e um indice aleatorio de um gene
            var chromosome_genes = chromosome.GetGenes();
            int index_gene = RandomizationProvider.Current.GetInt(0, chromosome_genes.Length);

            // Cria um novo cromossoma e escolhe também um indice aleatorio desse
            CarChromosome newChromosome = new CarChromosome(((CarChromosome)chromosome).getConfig());
            var newChromosome_genes = newChromosome.GetGenes();
            int index_gene_new = RandomizationProvider.Current.GetInt(0, newChromosome_genes.Length);

            // Substitui o gene selecionado no cromossoma original pelo gene selecionado do novo cromossoma
            chromosome_genes[index_gene] = newChromosome_genes[index_gene_new];

            // Substitui os genes do cromossomq original pelos genes mutados
            chromosome.ReplaceGenes(0, chromosome_genes);
        }
        
    }

}

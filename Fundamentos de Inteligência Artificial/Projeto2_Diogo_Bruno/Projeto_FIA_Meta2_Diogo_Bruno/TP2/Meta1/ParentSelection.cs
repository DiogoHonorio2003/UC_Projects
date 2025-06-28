using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using GeneticSharp.Domain.Chromosomes;
using GeneticSharp.Domain.Populations;
using GeneticSharp.Domain.Randomizations;
using GeneticSharp.Domain.Selections;
using GeneticSharp.Infrastructure.Framework.Texts;
using GeneticSharp.Runner.UnityApp.Car;
using UnityEngine;

public class ParentSelection : SelectionBase
{
    protected int num_torneio;

    public ParentSelection(int num_torneio) : base(2)
    {
        this.num_torneio = num_torneio;
    }

    protected override IList<IChromosome> PerformSelectChromosomes(int number, Generation generation)
    {

        IList<CarChromosome> population = generation.Chromosomes.Cast<CarChromosome>().ToList();
        IList<IChromosome> parents = new List<IChromosome>();

        // Seleção de elementos para o torneio
        while (parents.Count < number)
        {
            // Seleciona um numero random de participantes para o torneio
            List<CarChromosome> tournamentParticipants = new List<CarChromosome>();
            for (int i = 0; i < num_torneio; i++)
            {
                int randomIndex = RandomizationProvider.Current.GetInt(0, population.Count);
                tournamentParticipants.Add(population[randomIndex]);
            }

            // Verifica qual é que possui um maior fitness dentro de todos os participantes
            CarChromosome fittest = tournamentParticipants.OrderByDescending(c => c.Fitness).First();

            // Adiciona o com melhor fitness aos pais
            parents.Add(fittest);
        }

        return parents;
    }
}
